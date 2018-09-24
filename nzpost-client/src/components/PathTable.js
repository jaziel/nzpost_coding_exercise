import React from 'react';

export default function PathTable(paths) {
    let pathRows = paths.map((path) =>
      <tr key={path.source}>
        <td>{path.source}</td>
        <td>{path.destination}</td>
        <td>{path.distance}</td>
      </tr>
    );

    return (
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
    )
}