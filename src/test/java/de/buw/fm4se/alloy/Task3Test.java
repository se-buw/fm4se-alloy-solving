package de.buw.fm4se.alloy;

import org.junit.jupiter.api.Test;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.ast.Command;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;
import edu.mit.csail.sdg.translator.A4Options;
import edu.mit.csail.sdg.translator.A4Solution;
import edu.mit.csail.sdg.translator.TranslateAlloyToKodkod;
import edu.mit.csail.sdg.translator.A4Options.SatSolver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

class Task3Test {
	private static A4Reporter rep;
	private static A4Options opt;

	@BeforeAll
	public static void setUpOptions() {
		rep = A4Reporter.NOP;
		opt = new A4Options();
		opt.solver = SatSolver.SAT4J;
	}


	@Test
	void checkInv1SomeTrashedFile () {
		String addition = "one sig X in Trash {}";
		assertFalse(checkSat("inv1", addition), "A file in Trash should be a violation.");
	}

	@Test
	void checkInv1SomeFilesExist () {
		String addition = "fact { some File }";
		assertTrue(checkSat("inv1", addition), "No files can exist. This constraint is too strong.");
	}

	@Test
	void checkInv2AFileNotDeleted () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { Trash = F1}";
		assertFalse(checkSat("inv2", addition), "Some files are not deleted.");
	}

	@Test
	void checkInv2PossibleToDeleteFiles () {
		String addition = "";
		assertTrue(checkSat("inv2", addition), "The constraint is not satisfiable.");
	}

	@Test
	void checkInv3AFileIsDeleted () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { Trash = F1}";
		assertTrue(checkSat("inv3", addition), "Unable to delete a file.");
	}

	@Test
	void checkInv3NoDeletedFiles () {
		String addition = "fact { no Trash }";
		assertFalse(checkSat("inv3", addition), "Empty trash should be a violation.");
	}

	@Test
	void checkInv4AProtectedFileIsDeleted () {
		String addition = "one sig F1 extends File {}\n" + 
				"fact { F1 in Trash and F1 in Protected }";
		assertFalse(checkSat("inv4", addition), "A protected file should not be in trash.");
	}

	@Test
	void checkInv4SomeDeletedFile () {
		String addition = "one sig F1 extends File {}\n" + 
				"fact { F1 in Trash }";
		assertTrue(checkSat("inv4", addition), "Files should be possible to delete.");
	}

	@Test
	void checkInv5AnUnprotectedAndNotDeletedFile () {
		String addition = "one sig F1 extends File {}\n" + 
				"fact { not (F1 in Trash) and not (F1 in Protected) }";
		assertFalse(checkSat("inv5", addition), "No unprotected file should exist outside of Trash.");
	}

	@Test
	void checkInv5SomeDeletedUnprotectedFile () {
		String addition = "one sig F1 extends File {}\n" + 
				"fact { F1 in Trash and not (F1 in Protected) }";
		assertTrue(checkSat("inv5", addition), "Unprotected files should be possible to delete.");
	}

	@Test
	void checkInv6FileLinkingToTwoFiles () {
		String addition = "one sig F1, F2, F3 extends File {}\n" + 
				"fact { F1 -> F2 in link and F1 -> F3 in link }";
		assertFalse(checkSat("inv6", addition), "A file cannot link to two files.");
	}

	@Test
	void checkInv6FilesWithNoLinksPossible () {
		String addition = "one sig F1 extends File {}\n" + 
				"fact { no F1.link }";
		assertTrue(checkSat("inv6", addition), "Files with no links should be possible.");
	}

	@Test
	void checkInv6FilesWithOneLinkPossible () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { F2 = F1.link }";
		assertTrue(checkSat("inv6", addition), "Files with one link should be possible.");
	}

	@Test
	void checkInv7ALinkedFileIsDeleted () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { F2 in Trash and F1 -> F2 in link }";
		assertFalse(checkSat("inv7", addition), "A linked file should not be in trash.");
	}

	@Test
	void checkInv7LinkeFilesExist () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { F1 -> F2 in link }";
		assertTrue(checkSat("inv7", addition), "Linked files should be possible.");
	}

	@Test
	void checkInv8TwoFilesWithNoLinks () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { File = F1 + F2 and no F1.link and no F2.link }";
		assertTrue(checkSat("inv8", addition), "Two files with no links should be possible.");
	}

	@Test
	void checkInv8LinkeFilesExist () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { F1 -> F2 in link }";
		assertFalse(checkSat("inv8", addition), "Linked files should not be possible.");
	}

	@Test
	void checkInv9ConsecutiveLinkChain () {
		String addition = "one sig F1, F2, F3 extends File {}\n" + 
				"fact { F1 -> F2 in link and F2 -> F3 in link }";
		assertFalse(checkSat("inv9", addition), "A link chain cannot be longer than one.");
	}

	@Test
	void checkInv9LinkeFilesExist () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { F1 -> F2 in link }";
		assertTrue(checkSat("inv9", addition), "Linked files should be possible.");
	}

	@Test
	void checkInv10DeletedLinkWithoutDeletedTarget () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { F2 in F1.link and F1 in Trash and not (F2 in Trash) }";
		assertFalse(checkSat("inv10", addition), "If a link is deleted, the target should be deleted too.");
	}

	@Test
	void checkInv10LinkeAndDeletedFilesExist () {
		String addition = "one sig F1, F2 extends File {}\n" + 
				"fact { F1 -> F2 in link and F1 + F2 in Trash }";
		assertTrue(checkSat("inv10", addition), "Deleted, linked files should be possible.");
	}

	public boolean checkSat(String pred, String addition) {
		String code = FmPlay.getCodeFromPermalink(Tasks.task_3);
		code += "\n\n"+ addition + "\n\nrun " + pred + "\n\n";
		Module world = CompUtil.parseEverything_fromString(rep, code);
		
		// retrieve last command (the one we just added)
		int numCmd = world.getAllCommands().size();
		Command c = world.getAllCommands().get(numCmd - 1);

		A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), c, opt);
		return ans.satisfiable();
	}

}
