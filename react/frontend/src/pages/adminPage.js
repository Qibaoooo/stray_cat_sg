import React, { useEffect, useState } from "react";
import { Button, Modal, Table } from "react-bootstrap";
import { approveCatSighting, getPendingCatSightings, rejectCatSighting } from "./utils/api/apiCatSightings";
import { approveVerifications, getPendingVerifications, rejectVerifications } from "./utils/api/apiVerification";

const CatSightingsApprovalTable = ({ refreshSighting, sightings }) => {
  const [show, setShow] = useState(false);
  const [selected, setSelected] = useState({});

  const handleApprove = () => {
    approveCatSighting(selected.id).then(resp=>{
      console.log(resp)
      refreshSighting()
      setShow(false)
    }).catch(e=>{
      console.log(e)
    })
  };
  const handleReject = () => {
    rejectCatSighting(selected.id).then(resp=>{
      console.log(resp)
      refreshSighting()
      setShow(false)
    }).catch(e=>{
      console.log(e)
    })
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
            Sighting <a href={`/catDetails?id=${selected.cat}`}>{selected.sightingName}</a> is pending your approval
          </Modal.Title>
        </Modal.Header>
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
  );
};

const AdminPage = () => {
  const [sightings, SetSightings] = useState([]);
  const [verifications, SetVerifications] = useState([]);

  const refreshSighting = () =>{
    getPendingCatSightings().then((resp) => {
      console.log(resp.data);
      SetSightings(resp.data);
    })
  }
  useEffect(() => {
    refreshSighting()
  }, []);

  const refreshVerification = () =>{
    getPendingVerifications().then((resp) => {
      console.log(resp.data);
      SetVerifications(resp.data);
    })
  }
  useEffect(() => {
    refreshSighting()
    refreshVerification()
  }, []);



  return (
    <div className="col-8 mx-auto">
      <br></br>
      <h6>
        {" "}
        <u>Cat Sightings Pending Approval</u>
      </h6>
      <CatSightingsApprovalTable refreshSighting={refreshSighting} sightings={sightings} />
      <br></br>
      <h6>
        {" "}
        <u>Owner Verifications Pending Approval</u>
      </h6>
      <VerificationApprovalTable refreshVerification={refreshVerification} verification={verifications}/>
    </div>
  );
};

const VerificationApprovalTable = ({ refreshVerification, verification }) => {
  const [show, setShow] = useState(false);
  const [selected, setSelected] = useState({});

  const handleApprove = () => {
    approveVerifications(selected.id).then(resp=>{
      console.log(resp)
      refreshVerification()
      setShow(false)
    }).catch(e=>{
      console.log(e)
    })
  };
  const handleReject = () => {
    rejectVerifications(selected.id).then(resp=>{
      console.log(resp)
      refreshVerification()
      setShow(false)
    }).catch(e=>{
      console.log(e)
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
    <Table className="" striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Materials</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        {verification.map((verification, index, array) => {
          return (
            <tr>
              <td>{verification.id}</td>
              <td>{verification.imageURL}</td>
              
              <td>
                <DetailsButton verification={verification} />
              </td>
            </tr>
          );
        })}
      </tbody>
      <DetailsModal />
    </Table>
  );
};



export default AdminPage;
