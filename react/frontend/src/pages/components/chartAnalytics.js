import React, { useEffect, useState } from "react";
import { getAllCatSightings } from "../utils/api/apiCatSightings";
import { getAllComments } from "../utils/api/apiComment";
import { Button, Container, Row, Col } from "react-bootstrap";
import { Line } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, Filler } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, Filler);

const ChartAnalytics = () => {
    const [dataType, setDataType] = useState("Sightings");
    const [timeframe, setTimeframe] = useState("week");
    const [data, setData] = useState([]);
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: [
            {
                label: '',
                data: [],
                borderColor: 'rgb(75, 192, 192)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
            },
        ],
    });
    

    useEffect(() => {
        // Fetch data based on selected type
        let fetchData;
        switch (dataType) {
            case "Sightings":
                fetchData = getAllCatSightings();
                break;
            case "Comments":
                fetchData = getAllComments()
                break;
            case "User sign-up Count":
                // fetchData = getUserCount(); // Placeholder function, replace with actual API call
                break;
            default:
                fetchData = Promise.resolve({ data: [] });
        }

        fetchData.then((resp) => {
            setData(resp.data);
            processData(resp.data, timeframe, dataType);
        });
    }, [dataType, timeframe]);

    // Function to process data based on the selected timeframe
    const processData = (data, timeframe, dataType) => {
        // Group data by timeframe (week/day/month)
        const groupedData = {};
        data.forEach((item) => {
            const date = new Date(item.time);
            let key;
            switch (timeframe) {
                case "day":
                    key = date.toISOString().split('T')[0]; // YYYY-MM-DD
                    break;
                case "month":
                    key = date.getFullYear() + "-" + (date.getMonth() + 1).toString().padStart(2, '0'); // YYYY-MM
                    break;
                case "week":
                default:
                    const firstDay = new Date(date.setDate(date.getDate() - date.getDay()));
                    key = firstDay.toISOString().split('T')[0]; // First day of the week
                    break;
            }

            if (!groupedData[key]) {
                groupedData[key] = 0;
            }
            groupedData[key]++;
        });

        // Convert grouped data into arrays for chart
        const labels = Object.keys(groupedData).sort();
        const counts = labels.map(label => groupedData[label]);

        setChartData({
            labels,
            datasets: [
                {
                    label: `${dataType} per ${timeframe}`,
                    data: counts,
                    borderColor: 'rgb(75, 192, 192)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    fill: true,
                }
            ]
        });
    };

    return (
        <Container fluid className="d-flex flex-column align-items-center" style={{ maxWidth: '800px' }}>
            <Row className="w-100 mb-3">
                <Col xs={5}></Col>
                <Col xs={2} className="d-flex justify-content-center">
                    <select value={dataType} onChange={(e) => setDataType(e.target.value)} className="me-2">
                        <option value="Sightings">Sightings</option>
                        <option value="Comments">Comments</option>
                        <option value="User Count">User Count</option>
                    </select>
                    <select value={timeframe} onChange={(e) => setTimeframe(e.target.value)} className="me-2">
                        <option value="day">Day</option>
                        <option value="week">Week</option>
                        <option value="month">Month</option>
                    </select>
                </Col>
                <Col xs={5} className="d-flex justify-content-end">
                    <Button className="btn border-0 bg-secondary" href="/map"><p>Back to Map</p></Button>
                </Col>
            </Row>
            <Row className="w-100">
                <Col>
                    <div style={{ height: '500px' }}>
                        <Line
                            data={chartData}
                            options={{
                                animation: {
                                    duration: 1000,
                                },
                                scales: {
                                    x: {
                                        title: {
                                            display: true,
                                            text: 'Timeframe'
                                        }
                                    },
                                    y: {
                                        title: {
                                            display: true,
                                            text: 'Number of Entries' // Adjusted based on selected dataType
                                        }
                                    }
                                },
                                plugins: {
                                    legend: {
                                        display: true,
                                        position: 'top',
                                    },
                                    tooltip: {
                                        mode: 'index',
                                        intersect: false,
                                    },
                                },
                                responsive: true,
                                maintainAspectRatio: false,
                            }}
                        />
                    </div>
                </Col>
            </Row>
        </Container>
    );
};

export default ChartAnalytics;
