import React, { useState } from "react";
import { requireLoginUser } from "./utils/userinfo";
import { useEffect } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import ImagePicker from "./components/imagePicker";
import MapSidePanel from "./components/mapSidePanel";


const UploadVerification=()=>{
    const [validated, setValidated] = useState(false);
    const [imageURLs, setImageURLs] = useState([]);
    const [vectorMap, setVectorMap] = useState({});

    const handleSubmit = (event) => {
        event.preventDefault();
        

        if(imageURLs.length<1){
            alert("please at least upload one photo");
            return;
        }
        
        setValidated(true);
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
                    imageURLs={imageURLs}
                    setImageURLs={setImageURLs}
                    requireVectors={true}
                    vectorMap={vectorMap}
                    setVectorMap={setVectorMap}
                    maxImageCount={10}
                    
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
