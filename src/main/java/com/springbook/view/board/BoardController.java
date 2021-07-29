package com.springbook.view.board;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;

@Controller
@SessionAttributes("board") //세션에 board를 담는다 (쓰지 않는 것이 좋음)
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap(){
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}
	//conditionMap이 Model에 저장됨
	//모든 jsp페이지가 model에 저장되어있는 것을 사용가능
	
	
	@RequestMapping("/insertBoard.do")
	public String insertBoard(BoardVO vo) throws IOException {
//		System.out.println("여기여기여기");
//		MultipartFile uploadFile = vo.getUploadFile();
//		if(!uploadFile.isEmpty()) {
//			String fileName = uploadFile.getOriginalFilename();
//			uploadFile.transferTo(new File("C:/upload/" + fileName));
//		}
		boardService.insertBoard(vo);
		return "getBoardList.do";
	}
	
	@RequestMapping("/updateBoard.do")
	public String updateBoard(@ModelAttribute("board") BoardVO vo) {
		boardService.updateBoard(vo);
		return "getBoardList.do";
	}
	
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo) {
		boardService.deleteBoard(vo);
		return "getBoardList.do";
	}
	
	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {
		model.addAttribute("board", boardService.getBoard(vo)); // model 정보 저장
		return "getBoard.jsp";
		
	}
	
	
	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		
		if(vo.getSearchCondition() == null) vo.setSearchCondition("TITLE");
		if(vo.getSearchKeyword() == null) vo.setSearchKeyword("");
		
		model.addAttribute("boardList", boardService.getBoardList(vo));
		return "getBoardList.jsp";
	}
	
	
	
	
//	@RequestMapping("/getBoardList.do")
//	public String getBoardList(@RequestParam(value="searchCondition",
//				defaultValue="TITLE", required=false) String condition,
//				@RequestParam(value="searchKeyword", defaultValue="", required=false)
//				String keyword, BoardDAO boardDAO, Model model) {
//		System.out.println("검색 조건 : " + condition);
//		System.out.println("검색 단어 : " + keyword);
//		
////		model.addAttribute("boardList", boardDAO.getBoardList(vo)); // model 정보 저장
//		return "getBoardList.jsp";
//	}
}
