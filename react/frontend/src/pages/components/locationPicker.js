import GoogleMap from "google-maps-react-markers";
import { SingaporeGeoCoord } from "pages/utils/properties";
import React, { useRef, useState } from "react";
import { Button } from "react-bootstrap";

const LocationPicker = ({ center, setCenter }) => {
  let apiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;

  const mapRef = useRef();

  const [gettingLocation, setGettingLocation] = useState(false);

  const onGoogleApiLoaded = (map, maps) => {
    // console.log(map);
    mapRef.current = map;
  };

  const onSetToCurrentLocation = () => {
    setGettingLocation(true);
    navigator.geolocation.getCurrentPosition((p) => {
      // console.log(p);
      setCenter([p.coords.longitude, p.coords.latitude]);
      mapRef.current.map.setCenter({
        lat: p.coords.latitude,
        lng: p.coords.longitude,
      });
      setGettingLocation(false);
    });
  };

  const onMapChange = (map, maps) => {
    // console.log(map.center)
    setCenter(map.center);
  };

  return (
    <div>
      <p>
        <span className="m-3">Longitude: {center[0].toPrecision(6)}</span>

        <span className="m-3">Latitude: {center[1].toPrecision(6)}</span>
      </p>

      <GoogleMap
        apiKey={apiKey}
        options={{ clickableIcons: false }}
        defaultCenter={SingaporeGeoCoord}
        defaultZoom={11}
        onGoogleApiLoaded={onGoogleApiLoaded}
        onChange={onMapChange}
        mapMinHeight="30vh"
      >
        <h3>📍</h3>
      </GoogleMap>
      <Button
        size="sm"
        className="my-3 bg-secondary-subtle"
        variant="outline-info"
        onClick={onSetToCurrentLocation}
      >
        Set to current location
      </Button>
      {gettingLocation && (
        <p className="text-info">loading current location...</p>
      )}
    </div>
  );
};

export default LocationPicker;
