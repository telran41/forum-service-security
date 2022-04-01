package telran.java41.security.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java41.forum.dao.PostRepository;
import telran.java41.forum.model.Post;

@Service("customSecurity")
@AllArgsConstructor
public class CustomWebSecurity {
	
	PostRepository postRepository;
	
	public boolean checkPostAuthority(String postId, String userName) {
		Post post = postRepository.findById(postId).orElse(null);
		return post != null && userName.equals(post.getAuthor());
	}

}
