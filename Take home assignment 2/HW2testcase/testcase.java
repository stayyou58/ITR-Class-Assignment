// copy from here, replace the main method in your .java
// if not working, change every String compare in your code from "==" to ".equals()"
// e.g. str1 == str2 -> str1.equals(str2)
public static void compareTwo2DStringArray(String[][] a, String[][] b) {
    int TP = 0;
    int TALength = a.length;
    int studentLength = b.length;
    for (int i = 0; i < TALength; i++) {
        for (int j = 0; j < studentLength; j++) {
            if (Arrays.equals(a[i], b[j])) {
                TP++;
                break;
            }
        }
    }
    double precision = (double) TP / studentLength;
    double recall = (double) TP / TALength;
    double f1 = 2 * precision * recall / (precision + recall);
    System.out.printf("%.2f ", f1);
}

public static boolean validateVillagesandHeroes(Graph G, int[] villages, String[] heroes) {
    if (villages.length != heroes.length)
        return false;
    for (int i = 0; i < villages.length; i++) {
        if (G.heros[villages[i]].equals(heroes[i]) == false)
            return false;
    }
    return true;
}

public static void compareHeroRecruitments(HeroRecruitment[] a, HeroRecruitment[] b, Graph G) {
    int TP = 0;
    int TALength = a.length;
    int studentLength = b.length;
    for (int i = 0; i < TALength; i++) {
        for (int j = 0; j < studentLength; j++) {
            if (a[i].days == b[j].days && a[i].grade == b[j].grade && Arrays.equals(a[i].heros, b[j].heros)) {
                if (validateVillagesandHeroes(G, b[j].villages, b[j].heros))
                    TP++;
                break;
            }
        }
    }
    double precision = (double) TP / studentLength;
    double recall = (double) TP / TALength;
    double f1 = 2 * precision * recall / (precision + recall);
    System.out.printf("%.2f ", f1);

}

public static void compareTop3HeroRecruitments(HeroRecruitment[] a, HeroRecruitment[] b, Graph G) {
    int TP = 0;
    int TALength = a.length;
    int studentLength = b.length;
    for (int i = 0; i < TALength; i++) {
        for (int j = 0; j < studentLength; j++) {
            if (a[i].grade == b[j].grade && a[i].days == b[j].days && Arrays.equals(a[i].heros, b[j].heros)) {
                if (validateVillagesandHeroes(G, b[j].villages, b[j].heros))
                    TP++;
                break;
            }
        }
    }
    double precision = (double) TP / studentLength;
    double recall = (double) TP / TALength;
    double f1 = 2 * precision * recall / (precision + recall);
    System.out.printf("%.2f ", f1);

}

public static void main(String[] args) {
    String path = "C:\\Users\\..."; // put your test case directory here
    for (int testcase = 0; testcase < 4; testcase++) {
        try {
            /* Read file */
            File file = new File(path + "\\testcase" + testcase + ".txt");
            Scanner sc = new Scanner(file);
            int v = sc.nextInt();
            String[] hero = new String[v];
            for (int i = 0; i < v; i++) {
                hero[i] = sc.next();
            }
            int e = sc.nextInt();
            int[] edge = new int[e * 2];
            for (int i = 0; i < e * 2; i++) {
                edge[i] = sc.nextInt();
            }
            Graph G = new Graph(v, e, edge, hero);
            int enemyLength = sc.nextInt();
            String[] enemy = new String[enemyLength];
            for (int i = 0; i < enemyLength; i++) {
                enemy[i] = sc.next();
            }
            // read file into waysToWin_ans
            int waysToWin_ans_length = sc.nextInt();
            String[][] waysToWin_ans = new String[waysToWin_ans_length][enemyLength];
            for (int i = 0; i < waysToWin_ans_length; i++) {
                for (int j = 0; j < enemyLength; j++) {
                    waysToWin_ans[i][j] = sc.next();
                }
            }
            // read file into findPlansForRecruitment_ans
            int findPlansForRecruitment_ans_length = sc.nextInt();
            HeroRecruitment[] findPlansForRecruitment_ans = new HeroRecruitment[findPlansForRecruitment_ans_length];
            for (int i = 0; i < findPlansForRecruitment_ans_length; i++) {
                int grade = sc.nextInt();
                int days = sc.nextInt();
                int[] village = new int[enemyLength];
                String[] heros2 = new String[enemyLength];
                for (int j = 0; j < enemyLength; j++) {
                    heros2[j] = sc.next();
                }
                findPlansForRecruitment_ans[i] = new HeroRecruitment(days, grade, village, heros2);
            }
            HeroRecruitment[] top3HeroRecruitments_ans = new HeroRecruitment[3];
            for (int i = 0; i < 3; i++) {
                int grade = sc.nextInt();
                int days = sc.nextInt();
                int[] village = new int[enemyLength];
                String[] heros2 = new String[enemyLength];
                for (int j = 0; j < enemyLength; j++) {
                    heros2[j] = sc.next();
                }
                top3HeroRecruitments_ans[i] = new HeroRecruitment(days, grade, village, heros2);
            }
            sc.close();
            /* Read file end */

            // test waysToWin
            String[][] waysToWin_stu = waysToWin(enemy);
            compareTwo2DStringArray(waysToWin_ans, waysToWin_stu);

            // test findPlansForRecruitment
            HeroRecruitment[] findPlansForRecruitment_stu = findPlansForRecruitment(G, waysToWin_stu, enemy);
            compareHeroRecruitments(findPlansForRecruitment_ans, findPlansForRecruitment_stu, G);

            // test top3HeroRecruitments
            HeroRecruitment[] top3HeroRecruitments_stu = top3Plans(findPlansForRecruitment_stu);
            compareTop3HeroRecruitments(top3HeroRecruitments_ans, top3HeroRecruitments_stu, G);
            // System.out.print("| ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}