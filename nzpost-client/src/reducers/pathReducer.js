import {
  FETCH_CITIES_BEGIN,
  FETCH_CITIES_SUCCESS,
  FETCH_CITIES_FAILURE
} from './../actions/pathActions';

const pathReducer = (state = [], action) => {
    switch(action.type) {
        case FETCH_CITIES_BEGIN:
          return state;
        case FETCH_CITIES_SUCCESS:
          return action.payload;
        case FETCH_CITIES_FAILURE:
          return action.payload;
        default:
          return state;
      }
}
export default pathReducer;