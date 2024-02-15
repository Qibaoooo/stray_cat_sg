import React, { useState } from "react";
import { Button, Form } from "react-bootstrap";
import LocationPicker from "./locationPicker";
import ImagePicker from "./imagePicker";
import { SingaporeGeoCoord } from "pages/utils/properties";
import { createNewCatSightings } from "pages/utils/api/apiCatSightings";

const SightingForm = () => {
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

    createNewCatSightings(
      {
        sightingName: suggestedCatName,
        locationLat: center[1],
        locationLong: center[0],
        time: Date.now(),
        suggestedCatName: suggestedCatName,
        suggestedCatBreed: observedCatbreed,
        tempImageURLs: imageURLs,
        vectorMap: vectorMap
      }
    ).then(resp => {
      window.location.href = `/catDetails?id=${resp.data.cat}`
    })

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
        maxImageCount={10}
      ></ImagePicker>
      <hr></hr>
      <Form.Group className="mb-3" controlId="SuggestedCatName">
        <Form.Label>Give this cat a name:</Form.Label>
        <Form.Control type="text" required />
        <Form.Control.Feedback type="invalid">
          Please suggest a name for this sighting.
        </Form.Control.Feedback>
      </Form.Group>
      <Form.Group className="mb-3" controlId="ObservedCatbreed">
        <Form.Label>Suggest a breed for him/her:</Form.Label>
        <Form.Control type="text" required={false}/>
      </Form.Group>
      <hr></hr>
      <Form.Label>Set sighting location:</Form.Label>
      <LocationPicker center={center} setCenter={setCenter}></LocationPicker>
      <hr></hr>
      <Button className="bg-secondary-subtle" type="submit">
        Submit form
      </Button>
    </Form>
  );
};

export default SightingForm;
