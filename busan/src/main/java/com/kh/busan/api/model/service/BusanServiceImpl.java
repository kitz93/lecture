package com.kh.busan.api.model.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kh.busan.api.model.mapper.CommentMapper;
import com.kh.busan.api.model.vo.CommentDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusanServiceImpl implements BusanService {

	private final CommentMapper mapper;

	@Override
	public String getBusan(int page) {
		String url = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
	           url += "?serviceKey=df5M4SAgya2aSXsY7%2BnX4MCmzrF7FLRXDwjBElrJo0xkOUnjgKknaQrr1H2%2FtYeyKafRwxvocScKN2wbjxrxew%3D%3D";
	           url += "&numOfRows=6";
	           url += "&pageNo=" + page;
	           url += "&resultType=json";
		URI uri =null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		return response;
	}

	@Override
	public String getBusanDetail(int pk) {
		String url = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
	           url += "?serviceKey=df5M4SAgya2aSXsY7%2BnX4MCmzrF7FLRXDwjBElrJo0xkOUnjgKknaQrr1H2%2FtYeyKafRwxvocScKN2wbjxrxew%3D%3D";
	           url += "&numOfRows=10";
	           url += "&pageNo=" + 1;
	           url += "&resultType=json";
	           url += "&UC_SEQ=" + pk;
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(uri, String.class);
		return response;
	}

	@Override
	public void save(CommentDTO comment) {
		mapper.save(comment);
	}

	@Override
	public List<CommentDTO> getComments(Long foodNo) {
		return mapper.getComments(foodNo);
	}

}
