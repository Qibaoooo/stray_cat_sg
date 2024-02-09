import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import ImagePicker from "./imagePicker";
import { SingaporeGeoCoord } from "pages/utils/properties";
import { sendCatVector } from "pages/utils/api/apiLostCat";
import { useHistory } from "react-router-dom";

const LostCatForm = () => {
  const [validated, setValidated] = useState(false);
  const [imageURLs, setImageURLs] = useState([]);
  const [vectorMap, setVectorMap] = useState({});

  const [center, setCenter] = useState([
    SingaporeGeoCoord.lng,
    SingaporeGeoCoord.lat,
  ]);

  const handleSubmit = (event) => {
    event.preventDefault();
    
    const form = event.currentTarget;
    
    if (form.checkValidity() === false) {
      event.stopPropagation();
      return
    }
    
    const suggestedCatName = (document.getElementById("SuggestedCatName").value)
    const observedCatbreed = (document.getElementById("ObservedCatbreed").value)
    
    if (imageURLs.length < 1) {
      alert("please at least upload one photo");
      return;
    }

    sendCatVector(
      {
        vectorMap: vectorMap,
        tempImageURLs: imageURLs,
        suggestedCatName: suggestedCatName,
        suggestedCatBreed: observedCatbreed,
      }).then(
        resp=>{
          console.log(resp);
        }
      )


    setValidated(true);
  };

  return (
    <Form
      noValidate
      validated={validated}
      onSubmit={handleSubmit}
      style={{ maxWidth: "50%", margin: "auto", marginTop: "10px" }}
    >
      <ImagePicker
        imageURLs={imageURLs}
        setImageURLs={setImageURLs}
        requireVectors={true}
        vectorMap={vectorMap}
        setVectorMap={setVectorMap}
      ></ImagePicker>
      <hr></hr>
      <Form.Group className="mb-3" controlId="SuggestedCatName">
        <Form.Label>Name of your cat:</Form.Label>
        <Form.Control type="text" required />
        <Form.Control.Feedback type="invalid">
          Please suggest a name for this sighting.
        </Form.Control.Feedback>
      </Form.Group>
      <Form.Group className="mb-3" controlId="ObservedCatbreed">
        <Form.Label>Breed of your cat:</Form.Label>
        <Form.Control type="text" required={false}/>
      </Form.Group>
      <hr></hr>
      <Button className="bg-secondary-subtle" type="submit">
        Submit form
      </Button>
    </Form>
  );
};

export default LostCatForm;
