import {
  FETCH_STATIONS_BEGIN,
  FETCH_STATIONS_SUCCESS,
  FETCH_STATIONS_FAILURE
} from './../actions/pathActions';

const pathReducer = (state = [], action) => {
    switch(action.type) {
        case FETCH_STATIONS_BEGIN:
          return state;
        case FETCH_STATIONS_SUCCESS:
          return action.payload;
        case FETCH_STATIONS_FAILURE:
          return action.payload;
        default:
          return state;
      }
}
export default pathReducer;