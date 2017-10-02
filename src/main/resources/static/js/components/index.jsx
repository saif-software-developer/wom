import React from 'react';
import ReactDom from 'react-dom';
import App from './app.jsx';
//require('../sass/app.scss');
//import  '../sass/app.scss';
//import styles from  '../dist/app.css';

const mainElement = document.querySelector('main');
ReactDom.render(<App/>, mainElement);