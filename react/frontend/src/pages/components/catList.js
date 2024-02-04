import React, { useEffect, useState } from "react";
import { Button, Row, Stack } from "react-bootstrap";
import CatListTable from "./catListTable";

const CatList = () => {
  const [currentViewType, SetCurrentViewType] = useState("cat");

  const onToggleView = ()=>{
    if (currentViewType === "cat") {
      SetCurrentViewType("sighting")
    } else {
      SetCurrentViewType("cat")
    }
  }

  return (
    <Stack style={{}}>
      <Row style={{ textAlign: "start" }}>
        <div className="p-5">
          <Button 
          className="bg-secondary-subtle" 
          size="lg"
          onClick={onToggleView}
          >{currentViewType}</Button>
          <span className="mx-3">
            Toggle between views of cats and cat sightings.
          </span>
          <CatListTable viewType={currentViewType} />
        </div>
      </Row>
    </Stack>
  );
};

export default CatList;
