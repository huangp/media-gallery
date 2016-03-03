import { browserHistory } from 'react-router'

export const replaceRouteQuery = (location, paramsToReplace) => {
  const newLocation = {
    ...location,
    query: {
      ...location.query,
      ...paramsToReplace
    }
  }
  Object.keys(newLocation.query).forEach(key => {
    if (!newLocation.query[key]) {
      delete newLocation.query[key]
    }
  })
  browserHistory.replace({
    ...newLocation
  })
}
