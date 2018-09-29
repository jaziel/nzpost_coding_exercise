import React, { Component } from 'react';
import { connect } from 'react-redux';
import { fetchStations, fetchPath } from '../actions/pathActions' 

class PathForm extends Component {

  constructor() {
    super();
    this.state = {
      source: '',
      destination: ''
    }
  }


  handleSubmit = (e) => {
    e.preventDefault();
    const source = this.state.source;
    const destination =  this.state.destination;
    this.props.fetchPath(source, destination);

  }

  handleChangeSource = (e) => {
    this.setState({source: e.target.value});
  }

  handleChangeDestination = (e) => {
    this.setState({destination: e.target.value});
  }

  render() {

    let stations = this.props.stations;
    let optionItems = stations.length !== 0 ? (stations.map((station) =>
            <option key={station.name} value={station.name}>{station.name}</option>
    )) : null;
    let path = this.props.path;
    let pathRows =  path.length !== 0 ? (path.map((path) =>
      <tr key={path.source}>
        <td>{path.source}</td>
        <td>{path.destination}</td>
        <td>{path.distance}</td>
      </tr>
    )) : null;

    return (
      <div>
        <h1>Find Shortest Path</h1>
        <form onSubmit={this.handleSubmit}> 
        <select required value={this.state.source} onChange={this.handleChangeSource}>
          <option value="">Select a Source Station</option>
          {optionItems}
        </select><br /><br />
        <select required value={this.state.destination} onChange={this.handleChangeDestination}>
          <option value="">Select a Destination Station</option>
          {optionItems}
        </select><br /><br />
        <button>Find Path</button>
        </form>
        <div>
          <h1>Shortest Path </h1>
          <table className="PathForm-table">
            <thead>
              <tr>
                <th>Source</th>
                <th>Destination</th>
                <th>Distance</th>
              </tr>
            </thead>
            <tbody>
                {pathRows}
            </tbody>
          </table>
        </div>
      </div>
    );
  }

}

function mapStateToProps(state) {
  return {
    stations: state.stations,
    path: state.path
  }
}

export default connect(mapStateToProps, {fetchStations, fetchPath})(PathForm);