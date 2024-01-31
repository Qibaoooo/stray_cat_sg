from flask import Flask, request, jsonify
from waitress import serve

import json
from json import JSONEncoder

import requests
from PIL import Image
from io import BytesIO

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

@app.route('/comparevectors/', methods=['POST'])
def compare_vectors():
    data = request.get_json()
    
    vector1 = np.array(data['vector1'])
    vector2 = np.array(data['vector2'])

    # Ensure the vectors are 2D
    vector1 = vector1.reshape(1, -1)
    vector2 = vector2.reshape(1, -1)

    # Calculate cosine similarity
    similarity_score = cosine_similarity(vector1, vector2)[0][0]

    return jsonify({"cosine_similarity": similarity_score})


@app.route('/match/', methods = ['GET'])
def match_cat():
    #Take in 
    return


# run the server
if __name__ == '__main__':
    print("Starting the server.....")
    serve(app, host="0.0.0.0", port=80)

