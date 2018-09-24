import {
  FETCH_STATIONS_BEGIN,
  FETCH_STATIONS_SUCCESS,
  FETCH_STATIONS_FAILURE,
  FETCH_PATH_BEGIN,
  FETCH_PATH_SUCCESS,
  FETCH_PATH_FAILURE
} from './../actions/pathActions';

import initialState from './initialState'

const pathReducer = (state = initialState, action) => {
    switch(action.type) {
        case FETCH_STATIONS_BEGIN:
          return state;
        case FETCH_STATIONS_SUCCESS: {
          return {
            ...state,
            stations: action.payload,
          }
        }
        case FETCH_STATIONS_FAILURE:
          return action.payload;
        case FETCH_PATH_BEGIN:
          return state;
        case FETCH_PATH_SUCCESS: {
          return {
            ...state,
            path: action.payload,
          }
        }
        case FETCH_PATH_FAILURE:
          return action.payload;  
        default:
          return state;
      }
}
export default pathReducer;