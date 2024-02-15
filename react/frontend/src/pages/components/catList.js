import React, { useEffect, useState } from "react";
import { Button, Row, Stack, Form } from "react-bootstrap";
import CatListTable from "./catListTable";

const CatList = () => {
  const [currentViewType, SetCurrentViewType] = useState("sighting");
  const [filterOwnSightings, setFilterOwnSightings] = useState(false);

  const onToggleView = () => {
    if (currentViewType === "cat") {
      SetCurrentViewType("sighting");
    } else {
      SetCurrentViewType("cat");
    }
  };

  return (
    <Stack>
      <Row style={{ textAlign: "start" }}>
        <div className="p-5">
          <Button
            className="bg-secondary-subtle"
            size="lg"
            onClick={onToggleView}
          >
            {currentViewType}
          </Button>
          <span className="mx-3">
            Toggle between views of cats and cat sightings.
          </span>
          <div style={{ maxHeight: "80vh", overflow: "auto" }}>
          <CatListTable
              viewType={currentViewType}
              filterOwnSightings={filterOwnSightings}
              setFilterOwnSightings={setFilterOwnSightings}
            />
          </div>
        </div>
      </Row>
    </Stack>
  );
};

export default CatList;
