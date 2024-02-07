import { uploadSightingPhoto } from "pages/utils/api/apiAzureImage";
import { getVectorsOfImage } from "pages/utils/api/apiMachineLearning";
import { getFileType } from "pages/utils/fileUtil";
import React, { useRef, useState } from "react";
import { Button, Form } from "react-bootstrap";

const ImagePicker = ( { imageURLs , setImageURLs, requireVectors, vectorMap, setVectorMap }) => {
  const fileInputRef = useRef();
  const [images, setImages] = useState([]);

  const handleChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      
      const newImageFile = event.target.files[0];

      // filter for file type
      const fileType = getFileType(newImageFile.name)
      if (! ((fileType === "png") || (fileType === "jpg"))) {
        alert("only jpg and png files are supported.")
        return
      }

      // show mini size images uploaded
      setImages((oldArray) => [
        ...oldArray,
        URL.createObjectURL(newImageFile),
      ]);

      // IMPORTANT NOTE:
      // here we do NOT upload directly to the `images` container
      // because the user might close our page before filling up the form
      // and then we might have dirty data in our `images` container
      // So, we upload photos to `temp` container first.
      // When user click [submit form], we rename and move these photos to `images` container.
      // Also just use the current epoch time as the temp file name.
      uploadSightingPhoto(newImageFile, Date.now().toString(), fileType).then(
        (resp) => {
          const tempImageURL = resp.data;
          setImageURLs((oldArray) => [...oldArray, tempImageURL]);

          // only send request to ML api if needed.
          if (requireVectors) {
            getVectorsOfImage(tempImageURL).then((resp)=>{
              console.log(resp)
              const newVector = resp.data;
              let newPair = {}
              newPair[tempImageURL] = newVector
              setVectorMap({
                ...vectorMap,
                ...newPair
              })
            })
          }
        }
      );
    }
  };

  return (
    <>
      <p>Click + to add photos</p>

      {images.map((url, index, array) => {
        return (
          <img
            className="m-2"
            style={{ maxWidth: "100px" }}
            key={index}
            src={url}
          ></img>
        );
      })}
      <Button
        className="mx-3"
        variant="outline-dark"
        onClick={() => fileInputRef.current.click()}
      >
        +
      </Button>
      <p>
        {imageURLs.map((url, index, array) => {
          return (
            <a className="text-info m-2" href={url} key={index}>
              image blob link {index}
            </a>
          );
        })}
      </p>
      <input
        onChange={handleChange}
        multiple={false}
        ref={fileInputRef}
        type="file"
        hidden
      />
    </>
  );
};

export default ImagePicker;
