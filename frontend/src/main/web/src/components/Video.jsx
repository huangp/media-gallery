import React, {PropTypes} from 'react'

const width = 6 * 95 + 'px'
const style = {
  width: '100%',
  maxWidth: width,
  height: 'auto'
}

const Video = (props) => {
  return (
      <video className="video-js vjs-default-skin" controls
             preload="metadata" style={style}
             data-setup='{}'
          {...props}
      >
        <source src={props.src} type={props.type} />
      </video>
)}

Video.propTypes = {
  src: PropTypes.string.isRequired,
  type: PropTypes.string.isRequired
}

export default Video