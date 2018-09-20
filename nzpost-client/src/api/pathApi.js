import { BASE_URL } from '../config'

export function getAllStations() {
  let url = BASE_URL + '/stations/';
  return fetch(url)
  .then(handleErrors)
  .then(response => {
    return response.json();
  }).catch(error => {
    return error;
  });
}

export function getShortestPath(source, destination) {
  let url = BASE_URL +"/stations/path?source="+source+"&destination="+destination;
  return fetch(url).then(response => {
    return response.json();
  }).catch(error => {
    return error;
  });
}

// Handle HTTP errors since fetch won't.
function handleErrors(response) {
  if (!response.ok) {
    throw Error(response.statusText);
  }
  return response;
}
