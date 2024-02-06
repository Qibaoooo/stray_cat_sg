import { uploadSightingPhoto } from "pages/utils/api/apiAzureImage";
import { getFileType } from "pages/utils/fileUtil";
import React, { useRef, useState } from "react";
import { Button, Form } from "react-bootstrap";

const ImagePicker = ( { imageURLs , setImageURLs }) => {
  const fileInputRef = useRef();
  const [images, setImages] = useState([]);

  const handleChange = (event) => {
    if (event.target.files && event.target.files[0]) {
      
      // filter for file type
      const fileType = getFileType(event.target.files[0].name)
      if (! ((fileType === "png") || (fileType === "jpg"))) {
        alert("only jpg and png files are supported.")
        return
      }

      // show mini size images uploaded
      setImages((oldArray) => [
        ...oldArray,
        URL.createObjectURL(event.target.files[0]),
      ]);

      // IMPORTANT NOTE:
      // here we do NOT upload directly to the `images` container
      // because the user might close our page before filling up the form
      // and then we might have dirty data in our `images` container
      // So, we upload photos to `temp` container first.
      // When user click [submit form], we rename and move these photos to `images` container.
      // Also just use the current epoch time as the temp file name.
      uploadSightingPhoto(event.target.files[0], Date.now().toString(), fileType).then(
        (resp) => {
          console.log(resp.data);
          setImageURLs((oldArray) => [...oldArray, resp.data]);
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
            <a className="text-info m-2" href={url}>
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
