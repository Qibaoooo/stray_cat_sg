import { useRef, useState } from 'react'
import GoogleMap from 'google-maps-react-markers'
import { SingaporeGeoCoord } from 'pages/utils/properties';
import CatSightingMarker from './CatSightingMarker';
import React from 'react';

const CatMap = () => {
    const mapRef = useRef(null)
    const [mapReady, setMapReady] = useState(false)

    let apiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;
    console.log(apiKey);
  
  
    const onGoogleApiLoaded = ({ map, maps }) => {
      mapRef.current = map
      setMapReady(true)
    }
  
    const onMarkerClick = (e, { markerId, lat, lng }) => {
      console.log('This is ->', markerId)
  
      // inside the map instance you can call any google maps method
      mapRef.current.setCenter({ lat, lng })
      // ref. https://developers.google.com/maps/documentation/javascript/reference?hl=it
    }
  
    const onDrag = (map) => {
      console.log(JSON.stringify(map.center));
    };
  
    return (
      <div style={{ height: "100vh", width: "100%" }}>
        <GoogleMap
          apiKey={apiKey}
          defaultCenter={SingaporeGeoCoord}
          defaultZoom={12}
          onDrag={onDrag}
          onGoogleApiLoaded={onGoogleApiLoaded}
          mapMinHeight="100vh"
          onChange={(map) => console.log('Map moved', map)}å  
        >
            <CatSightingMarker
                lat={SingaporeGeoCoord.lat}
                lng={SingaporeGeoCoord.lng}
            ></CatSightingMarker>
        </GoogleMap>
      </div>
    );
  }

export default CatMap