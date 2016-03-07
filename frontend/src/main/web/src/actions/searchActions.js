import { createAction } from 'redux-actions'
import { replaceRouteQuery } from '../utils/routeUtils'

import { CALL_API } from 'redux-api-middleware'

export const UPDATE_SEARCH_TERM = 'UPDATE_SEARCH_TERM'

export const updateSearchTerm = createAction(UPDATE_SEARCH_TERM)

export const searchTerm = (term) => {
  return (dispatch, getState) => {
    replaceRouteQuery(getState().routing.location, {
      term: term
    })
    dispatch(updateSearchTerm(term))
  }
}

export const RESOURCE_SUCCESS = 'RESOURCE_SUCCESS'

export const loadAllResources = () => {
  return {
    [CALL_API]: {
      endpoint: 'http://localhost:8000//fakeData/resources.json',
      method: 'GET',
      types: ['RESOURCE_REQUEST', RESOURCE_SUCCESS, 'RESOURCE_FAILURE']
    }
  }
}