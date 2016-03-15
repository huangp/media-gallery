import React, { Component, PropTypes } from 'react'
import { connect } from 'react-redux'
import Helmet from 'react-helmet'
import ReactList from 'react-list'
//import { ButtonLink, Icon, LoaderText, Select } from 'zanata-ui'
import { debounce } from 'lodash'
import { replaceRouteQuery } from '../utils/routeUtils'

import { loadAllVideos} from '../actions/VideoSearchActions'
import { searchTerm} from '../actions/GeneralSearchActions'

import Video from '../components/Video'

class Videos extends Component {

  constructor () {
    super()
    // Need to add the debounce to onScroll here
    // So it creates a new debounce for each instance
    //this.onScroll = debounce(this.onScroll, 100)
  }

  componentWillMount() {
    this.props.loadResources()
  }

  renderItem(index, key) {
    const resource = this.props.resources[index]
    const src = resource.url
    const type = resource.mime
    const width = 6 * 95 + 'px'
    return (
        <div key={key} className="row">
          <div className="col-md-6 col-md-offset-3">
            <h3>{resource.title}</h3>
            <Video src={src} type={type} width={width}/>
          </div>
        </div>)
  }

  render () {
    return (
        <div>
          <h2>All video resources</h2>
          <p className="vjs-no-js bg-danger">
            To view this video please enable JavaScript, and consider upgrading to a web browser
            that <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
          </p>
          <ReactList
              itemRenderer={::this.renderItem}
              length={this.props.resources.length}
              type='uniform'
          />
        </div>
    )
  }
}

Videos.propTypes = {
  loadResources: PropTypes.func.isRequired,
  resources: PropTypes.arrayOf(PropTypes.shape(
      {
        title: PropTypes.string.isRequired,
        url: PropTypes.string.isRequired,
        file: PropTypes.string.isRequired
      }
  )).isRequired
}

const mapStateToProps = (state) => {
  return {
    resources: state.search.resources
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    dispatch,
    onSearchTerm: (term) => dispatch(searchTerm(term)),
    loadResources: () => dispatch(loadAllVideos())
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Videos)
