import {createStore, applyMiddleware} from 'redux';  
import pathReducer from '../reducers/pathReducer';  
import thunk from 'redux-thunk';

export default function configureStore() {  
  return createStore(
    pathReducer,
    applyMiddleware(thunk)
  );
}