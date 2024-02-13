import React, { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import ImagePicker from "./imagePicker";
import { sendCatVector } from "pages/utils/api/apiLostCat";
import { useNavigate } from "react-router-dom";
import { getUserRole } from "pages/utils/userinfo";

const LostCatForm = () => {
  const [validated, setValidated] = useState(false);
  const [imageURLs, setImageURLs] = useState([]);
  const [vectorMap, setVectorMap] = useState({});
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    if (getUserRole() !== "ROLE_onwer") {
      alert("only approved onwers are allowed to use this function.");
      return
    }

    const form = event.currentTarget;

    if (form.checkValidity() === false) {
      event.stopPropagation();
      return;
    }

    const suggestedCatName = document.getElementById("SuggestedCatName").value;
    const observedCatbreed = document.getElementById("ObservedCatbreed").value;

    if (imageURLs.length < 1) {
      alert("please at least upload one photo");
      return;
    }

    sendCatVector({
      vectorMap: vectorMap,
      tempImageURLs: imageURLs,
      suggestedCatName: suggestedCatName,
      suggestedCatBreed: observedCatbreed,
    })
      .then((resp) => {
        const simplifiedMatches = JSON.stringify(resp);
        navigate("/result", { state: { matches: simplifiedMatches } });
      })
      .catch((e) => console.log(e));

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
        maxImageCount={1}
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
        <Form.Control type="text" required={false} />
      </Form.Group>
      <hr></hr>
      <Button className="bg-secondary-subtle" type="submit">
        Submit form
      </Button>
    </Form>
  );
};

export default LostCatForm;
