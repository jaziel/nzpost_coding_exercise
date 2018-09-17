import React, { Component } from 'react';
import { BASE_URL } from './config'

class PathForm extends Component {

  constructor() {
    super();
    this.state = {
      cities:[],
      source: '',
      destination: '',
      path: []
    }
  }

  componentDidMount() {
    let url = BASE_URL + '/cities/';
    fetch(url).then( results => {
      let data = results.json();
      return data;
    }).then( data => {
      let cities = data.map( city => {return city});
      this.setState(
        { cities: cities}
      );
    })
  }

  handleSubmit = (e) => {
    e.preventDefault();
    const source = this.state.source;
    const destination =  this.state.destination;

    let url = BASE_URL +"/cities/path?source="+source+"&destination="+destination;
    fetch(url).then( results => {
      let data = results.json();
      return data;
    }).then( path => {
      this.setState(
        { path: path}
      );
    })
  }

  handleChangeSource = (e) => {
    this.setState({source: e.target.value});
  }

  handleChangeDestination = (e) => {
    this.setState({destination: e.target.value});
  }

  render() {
    let cities = this.state.cities;
    let optionItems = cities.map((city) =>
            <option key={city.name}>{city.name}</option>
    );
    let path = this.state.path;
    let pathRows = path.map((path) =>
      <tr key={path.source}>
        <td>{path.source}</td>
        <td>{path.destination}</td>
        <td>{path.distance}</td>
      </tr>
    );

    return (
      <div>
        <h1>Find Shortest Path</h1>
        <form onSubmit={this.handleSubmit}> 
        <select required value={this.state.source} onChange={this.handleChangeSource}>
          <option value="">Select a Source City</option>
          {optionItems}
        </select><br /><br />
        <select required value={this.state.destination} onChange={this.handleChangeDestination}>
          <option value="">Select a Destination City</option>
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
export default PathForm;