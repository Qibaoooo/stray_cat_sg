import React, { useState } from "react";
import { getUserinfoFromLocal, requireLoginUser } from "./utils/userinfo";
import { useEffect } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import ImagePicker from "./components/imagePicker";
import MapSidePanel from "./components/mapSidePanel";
import { createNewVerification } from "./utils/api/apiVerification";


const UploadVerification=()=>{
    const [validated, setValidated] = useState(false);
    const [imageURL, setImageURL] = useState([]);
    const [vectorMap, setVectorMap] = useState({});
   

    const handleSubmit = (event) => {
        event.preventDefault();
        

        if(imageURL.length<1){
            alert("please at least upload one photo");
            return;
        }

        createNewVerification(
            {
            imageURL: imageURL[0],
            userId: getUserinfoFromLocal().id,
                
            }).then(() => {
                setValidated(true);
                alert("Submitted successfully");
                window.history.back(); 
            }).catch(error => {
                console.error('Verification creation failed:', error);
            });    
    };

    useEffect(()=>{
        requireLoginUser()
      })


    

    return(
        <Row className="g-0">
            <Col className="g-0" xs={8} >
                <Form
                noValidate
                validated={validated}
                onSubmit={handleSubmit}
                style={{ maxWidth: "50%", margin: "auto", marginTop: "200px", }}
                >
                <Form.Label style={{fontSize: "1.2rem",fontWeight: "bold"}}>
                    UPLOAD RELEVANT MATERIALS FOR VERIFICATION</Form.Label>
                <ImagePicker 
                    imageURLs={imageURL}
                    setImageURLs={setImageURL}
                    requireVectors={true}
                    vectorMap={vectorMap}
                    setVectorMap={setVectorMap}
                    maxImageCount={1}
                    
                ></ImagePicker>
               
                <Form.Label>Note: can be certificate given by pet store</Form.Label>
                <hr></hr>
                <Button className="bg-secondary-subtle" type="submit">
                    Submit
                </Button>
                </Form>
            </Col>
            <Col className="g-0" xs={4}>
                <MapSidePanel></MapSidePanel>
            </Col>
        </Row>
    ); 
  
};


export default UploadVerification
