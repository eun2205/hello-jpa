package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        /*
        * 엔티티의 생명주기
        * a. 비영속: 객체만 생성
        * b. 영속: 영속성 컨텍스트에 저장
        * c. 준영속: 영속성 컨텍스트에서 분리
        * d. 삭제: 객체 삭제
        * */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //트랙잭션 단위로 움직임
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try { //code영역

            // 1. 회원 등록
            /*
            // a. 비영속
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            // b.영속 (영속성 컨텍스트에 저장, 쿼리는 실제 X)
            em.persist(member);

            // c. 준영속 (영속성 컨텍스트에서 분리)
            //em.detach(member);
            */

            // 2. 회원 조회
            //Member findMember = em.find(Member.class, 1L);

            // 3. 회원 수정 (update 필요 X, 영속성 컨텍스트에서 엔티티와 스냅샷 비교하여 변경 감지)
            //findMember.setName("HelloJPA");

            //4. 회원 삭제
            //em.remove(findMember);

            //JPQL (엔티티 객체 지향 쿼리)
            /*
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }
            */

            //플러시 예제
            /*
            Member member = new Member(200L, "member200");
            em.persist(member);
            
            //트랙잭션 자동 호출 전에 미리 플러시 직접 호출
            em.flush();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            */

            /*
            Member member = new Member();
            member.setUsername("C");

            em.persist(member);
            */


            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            team.addMember(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("member.username = " + m.getUsername());
            }

            tx.commit();    //DB에 쿼리 날림
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
