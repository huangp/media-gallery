import React, { Component } from 'react'
import { connect } from 'react-redux'
import Helmet from 'react-helmet'
//import ReactList from 'react-list'
//import { ButtonLink, Icon, LoaderText, Select } from 'zanata-ui'
import { debounce } from 'lodash'
import { replaceRouteQuery } from '../utils/routeUtils'
/*import {
  EditableText,
  Header,
  Page,
  Row,
  ScrollView,
  TableCell,
  TableRow,
  TextInput,
  View
} from '../components'*/
/*import {
  glossaryChangeLocale,
  glossaryUpdateIndex,
  glossaryFilterTextChanged,
  glossaryGetTermsIfNeeded,
  glossarySelectTerm,
  glossaryUpdateField
} from '../actions/glossary'*/

import {searchTerm} from '../actions/searchActions'

class Videos extends Component {
  constructor () {
    super()
    // Need to add the debounce to onScroll here
    // So it creates a new debounce for each instance
    this.onScroll = debounce(this.onScroll, 100)
  }

  onScroll () {
    // Debounced by 100ms in super()
    if (!this.list) return
    const {
      dispatch,
      location
      } = this.props
    const loadingThreshold = 250
    const indexRange = this.list.getVisibleRange()
    const newIndex = indexRange[0]
    const newIndexEnd = indexRange[1]
    replaceRouteQuery(location, {
      index: newIndex
    })
    //dispatch(glossaryUpdateIndex(newIndex))
    //dispatch(glossaryGetTermsIfNeeded(newIndex))
    // If close enough, load the prev/next page too
    //dispatch(glossaryGetTermsIfNeeded(newIndex - loadingThreshold))
    //dispatch(glossaryGetTermsIfNeeded(newIndexEnd + loadingThreshold))
  }

  render () {
    const {
      filterText = '',
      termsLoading,
      termCount,
      scrollIndex = 0,
      statsLoading,
      transLocales,
      selectedTransLocale,
      onTranslationLocaleChange,
      onFilterTextChange
      } = this.props
    /*const reactList = <ReactList
      useTranslate3d
      itemRenderer={::this.renderItem}
      length={termCount}
      type='uniform'
      initialIndex={scrollIndex || 0}
      ref={c => this.list = c}
    />*/
    return (
      <div>This is for all the videos</div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    location: ownProps.location
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    dispatch,
    onSearchTerm: (term) => dispatch(searchTerm(term))
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Videos)
