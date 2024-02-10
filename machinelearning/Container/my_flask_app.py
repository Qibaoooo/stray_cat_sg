from flask import Flask, request, jsonify
from flask_cors import CORS
from waitress import serve

import json
from json import JSONEncoder

import requests
from PIL import Image
from io import BytesIO
from collections import defaultdict

#Machine learning dependencies
import tensorflow as tf
from tensorflow.keras.preprocessing import image
from tensorflow.keras.models import load_model
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity


#Importing Model
model = load_model("main.model")

def preprocess_image(img_url):
    # Fetch the image from the URL
    response = requests.get(img_url)
    if response.status_code != 200:
        raise Exception(f"Failed to fetch image from URL: {img_url}")

    # Read the image content
    img = Image.open(BytesIO(response.content))
    img = img.convert('RGB') 

    # Resize and process the image as before
    img = img.resize((224, 224))
    img_array = image.img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    return tf.keras.applications.mobilenet.preprocess_input(img_array)

def extract_features(model, img_url):
    img = preprocess_image(img_url)  # Updated to use img_url
    features = model.predict(img)
    return features.flatten()


class NumpyArrayEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        return JSONEncoder.default(self, obj)
    

app = Flask(__name__)
CORS(app)

@app.route("/")
def hello():
    return "Hello world!"

@app.route('/sayhello/', methods=['GET'])
def say_hello():
    name = request.args.get('name') or request.form.get('name')    
    return "Hello " + str(name or '')


@app.route('/getembedding/', methods=['GET'])
def get_embedding():
    filename = request.args.get('filename') or request.form.get('filename')
    embedding = extract_features(model,filename)
    encodedNumpyData = json.dumps(embedding, cls=NumpyArrayEncoder)
    return encodedNumpyData


# Takes in two image url and return cosine similarity
@app.route('/getsimilarity/', methods = ['GET'])
def get_cosine():
    img_url_1 = request.args.get('img_url_1')
    img_url_2 = request.args.get('img_url_2')

    # Process the images and extract features
    features1 = extract_features(model, img_url_1)
    features2 = extract_features(model, img_url_2)

    # Calculate cosine similarity
    similarity_score = cosine_similarity([features1], [features2])[0][0]

    return jsonify({"cosine_similarity": similarity_score})


#comparevectors will take in json in this format
# {
#     "vector1": [0.1, 0.2, 0.3, ...],
#     "vector2": [0.4, 0.5, 0.6, ...]
# }
# and returns a cosine similarity
@app.route('/comparesinglevector/', methods=['POST'])
def compare_single_vectors():
    data = request.get_json()
    
    vector1 = np.array(data['vector1'])
    vector2 = np.array(data['vector2'])

    # Ensure the vectors are 2D
    vector1 = vector1.reshape(1, -1)
    vector2 = vector2.reshape(1, -1)

    # Calculate cosine similarity
    similarity_score = cosine_similarity(vector1, vector2)[0][0]

    return jsonify({"cosine_similarity": similarity_score})

@app.route('/comparemultiplevector/', methods=['POST'])
def compare_multi_vectors():
    data = request.get_json()

    query_vector = np.array(data["query_vector"])
    vectors_dict = data["vectors_dict"]

    vector_matrix = np.array(list(vectors_dict.values()))

    # Normalize the query vector and all vectors in the dictionary
    query_norm = query_vector / np.linalg.norm(query_vector)
    vectors_norm = vector_matrix / np.linalg.norm(vector_matrix, axis=1, keepdims=True)
    
    # Calculate dot products
    dot_products = np.dot(vectors_norm, query_norm)

    # Create a dictionary of {filename: cosine_similarity} pairs
    cosine_similarities = {filename: dot_product for filename, dot_product in zip(vectors_dict.keys(), dot_products)}

    # Sort the dictionary by cosine similarity in descending order
    sorted_cosine_similarities = dict(sorted(cosine_similarities.items(), key=lambda item: item[1], reverse=True))
    

    print(sorted_cosine_similarities)
    return json.dumps({"cosine_similarities": sorted_cosine_similarities})

@app.route('/gettopsimilarcats/', methods = ["POST"])
def get_similar_cats():
    data = request.get_json()

    query_vector = np.array(data["query_vector"])
    vectors_dict = data["vectors_dict"]

    # Flatten the vectors and keep track of their group IDs and filenames
    all_vectors = []
    group_id_to_filenames = defaultdict(list)  # Maps group IDs to their filenames

    for group_id, photos in vectors_dict.items():
        for filename, vector in photos.items():
            all_vectors.append(vector)
            group_id_to_filenames[group_id].append(filename)  # Keep track of filenames under each group ID

    vector_matrix = np.array(all_vectors)

    # Normalize the query vector and all vectors in the dictionary
    query_norm = query_vector / np.linalg.norm(query_vector)
    vectors_norm = vector_matrix / np.linalg.norm(vector_matrix, axis=1, keepdims=True)

    # Calculate dot products
    dot_products = np.dot(vectors_norm, query_norm)

    # Assign dot products back to their respective group IDs and calculate average similarities
    group_similarities = defaultdict(list)
    currentIndex = 0
    for group_id, filenames in group_id_to_filenames.items():
        for _ in filenames:
            group_similarities[group_id].append(dot_products[currentIndex])
            currentIndex += 1

    # Calculate the average similarity for each group
    average_group_similarities = {group_id: np.mean(similarities) for group_id, similarities in group_similarities.items()}

    # Sort the groups by their average cosine similarity in descending order
    sorted_groups = sorted(average_group_similarities.items(), key=lambda item: item[1], reverse=True)

    # Return the top 5 groups
    top_5_groups = dict(sorted_groups[:5])

    
    return json.dumps({"top_similar_groups": top_5_groups})
   



# run the server
if __name__ == '__main__':
    print("Starting the server.....")
    serve(app, host="0.0.0.0", port=80)

