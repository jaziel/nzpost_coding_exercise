import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { createStore } from 'redux';
import { Provider } from 'react-redux';

import pathReducer from './reducers/pathReducer';

const store = createStore(pathReducer);

ReactDOM.render(
<Provider store={store}>
<App /> 
</Provider>, document.getElementById('root'));
