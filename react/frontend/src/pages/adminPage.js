import React, { useEffect, useState } from "react";
import { Button, Col, Modal, Row, Table } from "react-bootstrap";
import {
  approveCatSighting,
  getPendingCatSightings,
  rejectCatSighting,
} from "./utils/api/apiCatSightings";
import { approveVerifications, getPendingVerifications, rejectVerifications } from "./utils/api/apiVerification";
import MapSidePanel from "./components/mapSidePanel";

const CatSightingsApprovalTable = ({ refreshSighting, sightings }) => {
  const [show, setShow] = useState(false);
  const [selected, setSelected] = useState({});

  const handleApprove = () => {
    approveCatSighting(selected.id)
      .then((resp) => {
        console.log(resp);
        refreshSighting();
        setShow(false);
      })
      .catch((e) => {
        console.log(e);
      });
  };
  const handleReject = () => {
    rejectCatSighting(selected.id)
      .then((resp) => {
        console.log(resp);
        refreshSighting();
        setShow(false);
      })
      .catch((e) => {
        console.log(e);
      });
  };
  const handleClose = () => setShow(false);

  const DetailsButton = ({ sighting }) => {
    return (
      <u
        style={{ cursor: "pointer" }}
        onClick={() => {
          setShow(true);
          setSelected(sighting);
        }}
      >
        details
      </u>
    );
  };

  const DetailsModal = () => {
    return (
      <Modal show={show} onHide={handleClose} animation={false}>
        <Modal.Header closeButton>
          <Modal.Title>
            Sighting{" "}
            <a href={`/catDetails?id=${selected.cat}`}>
              {selected.sightingName}
            </a>{" "}
            is pending your approval
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {Object.entries(selected).map((entry, index, array) => {
            return (
              <p style={{ maxWidth: "100%", overflow: "scroll" }}>
                <span style={{ fontWeight: "bold" }}>{entry[0]}: </span>
                <span>{entry[1]}</span>
              </p>
            );
          })}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleApprove}>
            Approve
          </Button>
          <Button variant="primary" onClick={handleReject}>
            Reject
          </Button>
        </Modal.Footer>
      </Modal>
    );
  };

  return (
    <div style={{ maxHeight: '500px', overflow: 'auto' }}>
    <Table className="" striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Uploader</th>
          <th>Sighting Name</th>
          <th>Location</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {sightings.map((sighting, index, array) => {
          return (
            <tr>
              <td>{sighting.id}</td>
              <td>{sighting.scsUser}</td>
              <td>{sighting.sightingName}</td>
              <td>
                {sighting.locationLat},{sighting.locationLong}
              </td>
              <td>
                <DetailsButton sighting={sighting} />
              </td>
            </tr>
          );
        })}
      </tbody>
      <DetailsModal />
    </Table>
    </div>
  );
};

const AdminPage = () => {
  const [sightings, SetSightings] = useState([]);
  const [verifications, SetVerifications] = useState([]);

  const refreshSighting = () => {
    getPendingCatSightings().then((resp) => {
      console.log(resp.data);
      SetSightings(resp.data);
    });
  };
  useEffect(() => {
    refreshSighting();
  }, []);

  const refreshVerification = () => {
    getPendingVerifications().then((resp) => {
      console.log(resp.data);
      SetVerifications(resp.data);
    });
  };
  useEffect(() => {
    
    refreshVerification();
  }, []);

  return (
    <Row className="g-0">
      <Col className="g-0" xs={8}>
        <div className="col-8 mx-auto">
          <br></br>
          <h6>
            {" "}
            <u>Cat Sightings Pending Approval</u>
          </h6>
          <CatSightingsApprovalTable
            refreshSighting={refreshSighting}
            sightings={sightings}
          />
          <br></br>
          <h6>
            {" "}
            <u>Owner Verifications Pending Approval</u>
          </h6>
            <VerificationApprovalTable refreshVerification={refreshVerification} verification={verifications}/>
          
        </div>
      </Col>
      <Col className="g-0" xs={4}>
        <MapSidePanel />
      </Col>
    </Row>
  );
};

const VerificationApprovalTable = ({ refreshVerification, verification }) => {
  const [show, setShow] = useState(false);
  const [selected, setSelected] = useState({});
  const[selecedImage,setSelectedImage]=useState(null);

  const handleLinkClick=(imageURL)=>{setSelectedImage(imageURL);};

  const handleApprove = () => {
    approveVerifications(selected.id).then(resp=>{
      console.log(resp);
      refreshVerification();
      setShow(false);
    }).catch(e=>{
      console.log(e);
    })
  };
  const handleReject = () => {
    rejectVerifications(selected.id).then(resp=>{
      console.log(resp);
      refreshVerification();
      setShow(false);
    }).catch(e=>{
      console.log(e);
    })
  };
  const handleClose = () => setShow(false);

  const DetailsButton = ({ verification }) => {
    return (
      <u
        style={{ cursor: "pointer" }}
        onClick={() => {
          setShow(true);
          setSelected(verification);
        }}
      >
        details
      </u>
    );
  };

  const DetailsModal = () => {
    return (
      <Modal show={show} onHide={handleClose} animation={false}>
        <Modal.Body>
          {Object.entries(selected).map((entry, index, array) => {
            return (
              <p style={{maxWidth:"100%", overflow:"scroll"}}>
                <span style={{fontWeight:"bold"}}>{entry[0]}: </span>
                <span>{entry[1]}</span>
              </p>
            );
          })}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleApprove}>
            Approve
          </Button>
          <Button variant="primary" onClick={handleReject}>
            Reject
          </Button>
        </Modal.Footer>
      </Modal>
    );
  };

  return (
    <div style={{ maxHeight: '500px', overflow: 'auto' }}>
    <Table className="" striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Materials</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {verification.map((verification, index) => {
          return (
            <tr>
              <td>{verification.id}</td>
              <td>
              <a
                    href="#"
                    onClick={() => handleLinkClick(verification.imageURL)}
                    style={{ cursor: 'pointer' }}
                  >
                    {verification.imageURL}
                  </a>
              </td>
              
              <td>
                <DetailsButton verification={verification} />
              </td>
            </tr>
          );
        })}
      </tbody>
      <DetailsModal />
    </Table>
    <Modal show={selecedImage !== null} onHide={() => setSelectedImage(null)}>
        <Modal.Header closeButton>
          <Modal.Title>Material Image</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {selecedImage && <img src={selecedImage} alt="Material" style={{ width: '100%' }} />}
        </Modal.Body>
      </Modal>
    </div>
  );
};



export default AdminPage;
