import { BrowserRouter, Routes, Route } from "react-router-dom"
import WelecomeComponent from "./WelecomeComponent"
import ListPostsComponent from "./ListPostsComponent"
import PostsSaveComponent from "./PostsSaveComponent"
import './PostsApp.css'
import HeaderComponent from "./HeaderComponent"
export default function PostsApp(){
    return(
        <div className="PostApp">
            <BrowserRouter>
            <HeaderComponent/>
            <Routes>
                <Route path ='/' element={<WelecomeComponent/>}></Route>
                <Route path ='/posts' element={<ListPostsComponent/>}></Route>
                <Route path ='/posts/save' element={<PostsSaveComponent/>}></Route>
                
            </Routes>
            </BrowserRouter>
        </div>
    )
}



