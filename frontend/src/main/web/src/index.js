import React from 'react'
import { render } from 'react-dom'
import { createStore, applyMiddleware, compose } from 'redux'
import createLogger from 'redux-logger'
import { routerMiddleware, syncHistoryWithStore } from 'react-router-redux'
import { Router, useRouterHistory, hashHistory, browserHistory } from 'react-router'
import WebFont from 'webfontloader'
import { apiMiddleware } from 'redux-api-middleware'
import { Provider } from 'react-redux'
import ReduxToastr from 'react-redux-toastr'
import thunk from 'redux-thunk'
import rootReducer from './reducers'
import routes from './routes'

// import "./style/style.less"
//style.use();

import 'video.js/dist/video-js.min.css'

WebFont.load({
  google: {
    families: [
      'Source Sans Pro:200,400,600',
      'Source Code Pro:400,600'
    ]
  },
  timeout: 2000
})

const mountNode = document.getElementById('main')
const basename = mountNode.getAttribute('data-app-basename') || ''

const configs = {
  basename
}

// TODO use hashHistory instead
const store = createStore(
  rootReducer(configs),
  applyMiddleware(
    thunk,
    apiMiddleware,
    routerMiddleware(browserHistory),
    createLogger()
  )
)

// Create an enhanced history that syncs navigation events with the store
const history = syncHistoryWithStore(hashHistory, store)


render(
  <Provider store={store}>
    <div>
      <Router routes={routes} history={history} />
      <ReduxToastr />
    </div>
  </Provider>, mountNode
)
