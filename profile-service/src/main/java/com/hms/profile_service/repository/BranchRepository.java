package com.hms.profile_service.repository;

import com.hms.profile_service.model.Branch;
import com.hms.profile_service.projection.BranchHierarchyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByStatus(String status);

    @Query(value = """
        SELECT 
            b.id AS branchId,
            b.branch_name AS branchName,
            b.location AS location,
            b.status AS status,
            b.type AS type,
            parent.branch_name AS parentBranchName,

            (
                SELECT JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'spaceId', sp.id,
                        'spaceName', sp.name,
                        'spaceType', spt.code,
                        'assets',
                        COALESCE(
                            (
                                SELECT JSON_ARRAYAGG(
                                    JSON_OBJECT(
                                        'assetId', a.id,
                                        'assetName', a.asset_code,
                                        'assetType', at.code
                                    )
                                )
                                FROM asset a
                                JOIN asset_type at ON at.id = a.asset_type_id
                                WHERE a.space_id = sp.id
                            ),
                            JSON_ARRAY()
                        )
                    )
                )
                FROM space sp
                JOIN space_type spt ON spt.id = sp.space_type_id
                WHERE sp.branch_id = b.id
            ) AS spaces,

            (
                SELECT JSON_ARRAYAGG(
                    JSON_OBJECT(
                        'userId', u.id,
                        'userName', u.userName,
                        'roles',
                        (
                            SELECT JSON_ARRAYAGG(r.name)
                            FROM user_roles ur
                            JOIN roles r ON r.id = ur.role_id
                            WHERE ur.user_id = u.id
                        )
                    )
                )
                FROM user_branch ub
                JOIN users u ON u.id = ub.user_id
                WHERE ub.branch_id = b.id
            ) AS users

        FROM branch b
        LEFT JOIN branch parent ON b.parent_id = parent.id
        """, nativeQuery = true)
    List<BranchHierarchyProjection> findBranchHierarchy();
}

