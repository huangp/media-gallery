import React, {PropTypes} from 'react'


const Video = (props) => {
  return (
      <video className="video-js vjs-default-skin" controls
             preload="metadata"
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