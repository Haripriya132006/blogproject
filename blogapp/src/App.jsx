import { useState } from 'react'

import Signincomp from './assets/Components/signincomp';
import Logincomp from './assets/Components/logincomp';
import Firstpage from './assets/Components/firstpagecomp';
import Postcomp from './assets/Components/postcomp';

import {BrowserRouter,Routes,Route,Link} from 'react-router-dom';
import PostDetails from './assets/Components/PostDetails';
import SearchUsers from './assets/Components/SearchUsers';
import Profile from './assets/Components/Profile';
import MyPosts from './assets/Components/MyPosts';
import EditPost from './assets/Components/EditPosts';
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/signin' element={<Signincomp />}/>
        <Route path='/' element={<Logincomp/>}/>
        <Route path='/first' element={<Firstpage/>}/>
        <Route path='/post' element={<Postcomp/>}/>
        <Route path="/postdetails" element={<PostDetails/>}/>
        <Route path="/searchusers" element={<SearchUsers/>}/>
        <Route path="/profile" element={<Profile/>}/>
        <Route path="/myposts" element={<MyPosts />} />        
        <Route path="/editpost/:postid" element={<EditPost />} />        </Routes>
    </BrowserRouter>

  )
}

export default App

