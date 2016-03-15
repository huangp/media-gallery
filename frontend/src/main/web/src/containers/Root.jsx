import React, { Component, PropTypes } from 'react' // eslint-disable-line
import { Provider } from 'react-redux'
import { Router, Route, Redirect } from 'react-router'
import App from '../containers/App'
import View from '../components/View'
import Photos from '../containers/Photos'
import Videos from '../containers/Videos'

export default class Root extends Component {
  render () {
    const {
      store,
      history
    } = this.props
    return (
      <Provider store={store}>
        <View>
          <Router history={history}>
            <Route component={App} >
              <Route path="videos" component={Videos} />
              <Redirect from="/" to="videos" />
              <Route path="photos" component={Photos} />
            </Route>
          </Router>
        </View>
      </Provider>
    )
  }
}

Root.propTypes = {
  store: PropTypes.object.isRequired,
  history: PropTypes.object.isRequired
}
