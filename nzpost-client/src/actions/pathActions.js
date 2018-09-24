import { getAllStations, getShortestPath } from '../api/pathApi';

export const FETCH_STATIONS_BEGIN   = 'FETCH_STATIONS_BEGIN';
export const FETCH_STATIONS_SUCCESS = 'FETCH_STATIONS_SUCCESS';
export const FETCH_STATIONS_FAILURE = 'FETCH_STATIONS_FAILURE';

export const FETCH_PATH_BEGIN   = 'FETCH_PATH_BEGIN';
export const FETCH_PATH_SUCCESS = 'FETCH_PATH_SUCCESS';
export const FETCH_PATH_FAILURE = 'FETCH_PATH_FAILURE';

export const fetchStationsBegin = () => ({
  type: FETCH_STATIONS_BEGIN
});

export const fetchStationsSuccess = stations => ({
  type: FETCH_STATIONS_SUCCESS,
  payload: stations
});

export const fetchStationsFailure = error => ({
  type: FETCH_STATIONS_FAILURE,
  payload: error 
});


export const fetchPathBegin = () => ({
  type: FETCH_PATH_BEGIN
});

export const fetchPathSuccess = path => ({
  type: FETCH_PATH_SUCCESS,
  payload: path
});

export const fetchPathFailure = error => ({
  type: FETCH_PATH_FAILURE,
  payload: error 
});

export function fetchStations() {
  return dispatch => {
    dispatch(fetchStationsBegin());
    return getAllStations()
    .then(stations => {
      dispatch(fetchStationsSuccess(stations));
    })
    .catch(error => dispatch(fetchStationsFailure(error)));
  };

}

export function fetchPath(source, destination) {
  return dispatch => {
    dispatch(fetchPathBegin());
    return getShortestPath(source, destination)
    .then(path => {
      dispatch(fetchPathSuccess(path));
    })
    .catch(error => dispatch(fetchPathFailure(error)));
  };
}