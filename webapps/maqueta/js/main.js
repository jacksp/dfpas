// var React  = require('react');
import React from 'react';
import ReactDOM from 'react-dom';

var Saludo= React.createClass({
          render:function(){
            return <div>&iexcl; Hola {this.props.nombre} ! </div>;
          }

      });

ReactDOM.render(<Saludo nombre="Alberto"/>, document.getElementById('root'));
