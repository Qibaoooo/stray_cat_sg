import React from 'react'

const CatCommentPanel = ({ isApproved }) => {
  return (
    <div style={{ marginTop:"50%"}}>
      {isApproved ? "Cat Comments Goes Here" : "Comment will be avaiable after admin approval"}
    </div>
  )
}

export default CatCommentPanel