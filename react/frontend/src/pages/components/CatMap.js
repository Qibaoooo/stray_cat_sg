import { useEffect, useRef, useState } from 'react'
import GoogleMap from 'google-maps-react-markers'
import { SingaporeGeoCoord } from 'pages/utils/properties';
import CatSightingMarker from './catSightingMarker';
import React from 'react';
import { getAllCatSightings } from 'pages/utils/api/apiCatSightings';

const CatMap = () => {
    const mapRef = useRef(null)
    // const [mapReady, setMapReady] = useState(false)
    const [catSightings,SetCatSightings] = useState([])

    let apiKey = process.env.REACT_APP_GOOGLE_MAPS_API_KEY;
  
    const onGoogleApiLoaded = ({ map, maps }) => {
      mapRef.current = map
      console.log((map))
      // setMapReady(true)
    }

    useEffect(()=>{
      getAllCatSightings()
      .then(resp => {
        console.log(resp.data);
        SetCatSightings(resp.data);
      })
      .catch(e=>{
        alert("please login first.")
        window.location.href = "/login"
      })
    }, [])
  
    // const onMarkerClick = (e, { markerId, lat, lng }) => {
    //   console.log('This is ->', markerId)
  
    //   // inside the map instance you can call any google maps method
    //   mapRef.current.setCenter({ lat, lng })
    //   // ref. https://developers.google.com/maps/documentation/javascript/reference?hl=it
    // }
  
    return (
      <div style={{ height: "100vh", minWidth: "60%" }}>
        <GoogleMap
          apiKey={apiKey}
          options={{ clickableIcons:false }}
          defaultCenter={SingaporeGeoCoord}
          defaultZoom={12}
          onGoogleApiLoaded={onGoogleApiLoaded}
          mapMinHeight="100vh"          
        >
            {catSightings.map((catSighting, index, array) => {
              // this is needed so that the marker does not appear on top of infowindow
              let z = -1 * parseInt((catSighting.locationLat * 10000 + "").split(".")[0])
              // console.log(z)

              return <CatSightingMarker 
              key={index}
              lat={catSighting.locationLat}
              lng={catSighting.locationLong}
              sighting={catSighting}
              optimizad={false}
              zIndex={z} 
              />
            })}
        </GoogleMap>
      </div>
    );
  }

export default CatMap