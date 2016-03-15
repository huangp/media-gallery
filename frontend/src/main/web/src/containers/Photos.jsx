import React, { Component, PropTypes } from 'react'
import { connect } from 'react-redux'
import Helmet from 'react-helmet'
import { debounce } from 'lodash'
import ReactList from 'react-list'


import {searchTerm, loadAllResources} from '../actions/GeneralSearchActions'

class Photos extends Component {

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
    return (
        <div key={key}>
            <p class="bg-success">
                {this.props.resources[index].title}
            </p>
        </div>)
  }

  render () {
    return (
        <div>
          <h2>All photo resources</h2>
          <ReactList
              itemRenderer={::this.renderItem}
              length={this.props.resources.length}
              type='uniform'
          />
        </div>
    )
  }
}

Photos.propTypes = {
    loadResources: PropTypes.func.isRequired,
    resources: PropTypes.arrayOf(PropTypes.shape(
        {
            title: PropTypes.string.isRequired,
            url: PropTypes.string.isRequired
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
    loadResources: () => dispatch(loadAllResources())
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Photos)