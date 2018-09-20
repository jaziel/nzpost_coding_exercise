import { getAllStations } from '../api/pathApi';

export const FETCH_STATIONS_BEGIN   = 'FETCH_STATIONS_BEGIN';
export const FETCH_STATIONS_SUCCESS = 'FETCH_STATIONS_SUCCESS';
export const FETCH_STATIONS_FAILURE = 'FETCH_STATIONS_FAILURE';

export const fetchStationsBegin = () => ({
  type: FETCH_STATIONS_BEGIN
});

export const fetchStationsSuccess = stations => ({
  type: FETCH_STATIONS_SUCCESS,
  payload: { stations }
});

export const fetchStationsFailure = error => ({
  type: FETCH_STATIONS_FAILURE,
  payload: { error }
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