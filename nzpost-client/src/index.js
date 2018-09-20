import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { Provider } from 'react-redux';

import { fetchStations } from './actions/pathActions'
import configureStore from './store/configureStore';  

const store = configureStore();
store.dispatch(fetchStations());

ReactDOM.render(
<Provider store={store}>
<App /> 
</Provider>, document.getElementById('root'));
