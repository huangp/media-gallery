import { createAction } from 'redux-actions'
import { replaceRouteQuery } from '../utils/routeUtils'



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
