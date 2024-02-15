import React, { useEffect, useState } from "react";
import MapSidePanel from "./components/mapSidePanel";
import { Button, Col, Form, Modal, Row, Stack, Table } from "react-bootstrap";
import CatListTable from "./components/catListTable";
import {
  deleteCatSighting,
  getAllCatSightings,
  reassignCatSighting,
} from "./utils/api/apiCatSightings";
import { getAllCats } from "./utils/api/apiCat";

const DetailsModal = ({ sighting, show, setShow, action }) => {
  const [cats, SetCats] = useState([]);
  const [newCatId, setNewCatId] = useState(0);

  useEffect(() => {
    getAllCats().then((resp) => {
      SetCats(resp.data);
    });
  }, []);

  const handleClose = () => setShow(false);

  const handleUpdate = () => {
    // console.log("update sighting ", sighting.id, "to newCatId", newCatId);
    reassignCatSighting(sighting.id, newCatId).then((resp) => {
      window.location.reload();
    });
  };

  const handleDelete = () => {
    deleteCatSighting(sighting.id).then((resp) => {
      window.location.reload();
    });
  };

  return (
    <Modal show={show} onHide={handleClose} animation={false}>
      <Modal.Header closeButton>
        <Modal.Title>
          Details for{" "}
          <a href={`/catDetails?id=${sighting.cat}`}>{sighting.sightingName}</a>{" "}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {Object.entries(sighting).map((entry, index, array) => {
          return (
            <p key={index} style={{ maxWidth: "100%", overflow: "scroll" }}>
              <span style={{ fontWeight: "bold" }}>{entry[0]}: </span>
              <span>{entry[1]}</span>
            </p>
          );
        })}
      </Modal.Body>
      {action === "update" && (
        <Modal.Footer>
          <Form.Label>Select new cat id</Form.Label>
          <Form.Select
            size="sm"
            style={{ maxWidth: "100px" }}
            onChange={(e) => {
              // console.log("e.target.value", e.target.value);
              setNewCatId(e.target.value);
            }}
            defaultValue={""}
          >
            <option>{""}</option>
            {cats.map((cat, index, array) => {
              return <option key={index}>{cat.id}</option>;
            })}
          </Form.Select>
          <Button variant="primary" onClick={handleUpdate}>
            Update
          </Button>
        </Modal.Footer>
      )}
      {action === "delete" && (
        <Modal.Footer>
          <Button variant="primary" onClick={handleDelete}>
            delete
          </Button>
        </Modal.Footer>
      )}
    </Modal>
  );
};

const GroupCatSightingsTable = () => {
  const [sightings, SetSightings] = useState([]);
  const [selected, setSelected] = useState({});
  const [show, setShow] = useState(false);
  const [action, setAction] = useState("");

  useEffect(() => {
    getAllCatSightings().then((resp) => {
      SetSightings(resp.data);
    });
  }, []);

  return (
    <Stack>
      <Row style={{ textAlign: "start" }}>
        <div className="p-5">
          <span className="mx-3">
            Toggle between views of cats and cat sightings.
          </span>
          <div style={{ maxHeight: "80vh", overflow: "auto" }}>
            <Table
              className="my-3"
              striped
              bordered={false}
              hover
              variant="primary"
            >
              <thead>
                <tr>
                  <th>#</th>
                  <th>Sighting Name</th>
                  <th>Location</th>
                  <th>Time</th>
                  <th>Cat</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {sightings.map((sighting, index, array) => {
                  return (
                    <tr key={index}>
                      <td>
                        <img
                          src={sighting.imagesURLs[0]}
                          style={{ width: "50px" }}
                        ></img>
                      </td>
                      <td
                        onClick={() => {
                          window.location.href = `/catDetails?id=${sighting.cat}`;
                        }}
                        style={{ cursor: "pointer" }}
                      >
                        <u>{sighting.sightingName}</u>
                      </td>
                      <td>
                        {sighting.locationLat + ", " + sighting.locationLong}
                      </td>
                      <td>{sighting.time}</td>
                      <td>{sighting.cat}</td>
                      <td>
                        <Button
                          size="sm"
                          onClick={() => {
                            setSelected(sighting);
                            setAction("update");
                            setShow(true);
                          }}
                        >
                          update
                        </Button>
                        <span> </span>
                        <Button
                          className="bg-primary-subtle"
                          size="sm"
                          onClick={() => {
                            setSelected(sighting);
                            setAction("delete");
                            setShow(true);
                          }}
                        >
                          delete
                        </Button>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </Table>
          </div>
        </div>
      </Row>
      <DetailsModal
        sighting={selected}
        show={show}
        setShow={setShow}
        action={action}
      />
    </Stack>
  );
};

const GroupingPage = () => {
  return (
    <Row className="g-0">
      <Col className="g-0" xs={8}>
        <GroupCatSightingsTable />
      </Col>
      <Col className="g-0" xs={4}>
        <MapSidePanel />
      </Col>
    </Row>
  );
};

export default GroupingPage;
