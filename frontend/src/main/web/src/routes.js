import React from 'react'
import { Route, IndexRoute, Redirect } from 'react-router'
import App from './containers/App'
import Photos from './containers/Photos'
import Videos from './containers/Videos'

const routes = (
  <Route path='/' component={App}>
    <IndexRoute component={App}/>
    <Route path="videos" component={Videos}/>
    <Redirect from="/" to="videos"/>
    <Route path="photos" component={Photos}/>
  </Route>
)

export default routes

