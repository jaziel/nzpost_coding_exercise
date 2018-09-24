import React, { Component } from 'react';
import logo from './logo.png';
import './App.css';
import PathForm from './components/PathForm';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">NZPost Coding Task</h1>
        </header>

        <PathForm />
      </div>
    );
  }
}

export default App;
