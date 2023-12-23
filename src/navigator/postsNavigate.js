export function createPostsNavi(navigate){
    console.log("Post 생성 페이지로 슈슝~");
    navigate('/posts/save');
}

export function updatePostsNavi(navigate, id){
    console.log("Post 업데이트 페이지로 슈슝~");
    console.log(id);
    navigate(`/posts/update/${id}`)
}

export function deletePostsNavi(navigate, id){
    console.log("Post 삭제 페이지로 슈슝~");
    console.log(id);
    navigate(`/posts/delete/${id}`)
}
