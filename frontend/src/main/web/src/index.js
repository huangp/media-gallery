import 'babel-polyfill'
import React from 'react'
import { render } from 'react-dom'
import { isUndefined } from 'lodash'
import { createStore, applyMiddleware, compose } from 'redux'
//import thunk from 'redux-thunk'
import createLogger from 'redux-logger'
import { hashHistory, browserHistory } from 'react-router'
import { routerMiddleware, syncHistoryWithStore } from 'react-router-redux'
import WebFont from 'webfontloader'
import { apiMiddleware } from 'redux-api-middleware'
import rootReducer from './reducers'
import Root from './containers/Root'
import Configs from './constants/Configs'
//import 'zanata-ui/lib/styles/index.css'
//import './styles/base.css'
//import './styles/atomic.css'
//import './styles/extras.css'

WebFont.load({
  google: {
    families: [
      'Source Sans Pro:200,400,600',
      'Source Code Pro:400,600'
    ]
  },
  timeout: 2000
})

const finalCreateStore = compose(
  applyMiddleware(
    //thunk,
    apiMiddleware,
    routerMiddleware(browserHistory),
    createLogger()
  )
  //, DevTools.instrument()
)(createStore)

// TODO use hashHistory instead
const store = createStore(
  rootReducer,
  applyMiddleware(apiMiddleware, routerMiddleware(browserHistory), createLogger())
)

// Call and assign the store with no initial state
//const store = ((initialState) => {
//    const store = finalCreateStore(rootReducer, initialState)
//    if (module.hot) {
//    // Enable Webpack hot module replacement for reducers
//    module.hot.accept('./reducers', () => {
//      const nextRootReducer = require('./reducers')
//      store.replaceReducer(nextRootReducer)
//  })
//}
//return store
//})()

/**
 * Process attributes in dom element:id='main-content'
 *
 * base-url - base url for rest api
 * user - json object of user information.
 * See {@link org.zanata.rest.editor.dto.User}
 * data - json object of any information to be included.
 * e.g Permission {@link org.zanata.rest.editor.dto.Permission}, and View
 * dev - If 'dev' attribute exist, all api data will be retrieve
 * from .json file in test directory.
 */
var mountNode = document.getElementById('root')
var baseUrl = mountNode.getAttribute('base-url')
var user = JSON.parse(mountNode.getAttribute('user'))
var data = JSON.parse(mountNode.getAttribute('data'))
var dev = data.dev

// Replace with redux state
// base rest url, e.g http://localhost:8080/rest
Configs.baseUrl = baseUrl
Configs.data = data
// append with .json extension in 'dev' environment
Configs.urlPostfix = isUndefined(dev) ? '' : '.json?'
// see org.zanata.rest.editor.dto.User
Configs.user = user

render(
<Root store={store} history={hashHistory} />,
  document.getElementById('root')
)
