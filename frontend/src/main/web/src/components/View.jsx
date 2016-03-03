import React, { PropTypes } from 'react' // eslint-disable-line
//import { flattenClasses } from '../utils/styleUtils'

const classes = {
  base: {
    ai: 'Ai(st)',
    d: 'D(f)',
    fld: 'Fld(c)',
    flxs: 'Flxs(0)'
  }
}

const classNameValue = '' // flattenClasses(classes, theme)

const View = ({
  items,
  children,
  theme,
  ...props
  }) => (

  <div className={classNameValue} {...props} >
    {children}
  </div>
)

export default View
